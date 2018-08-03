/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FrTestModule } from '../../../test.module';
import { InsumoDetailComponent } from '../../../../../../main/webapp/app/entities/insumo/insumo-detail.component';
import { InsumoService } from '../../../../../../main/webapp/app/entities/insumo/insumo.service';
import { Insumo } from '../../../../../../main/webapp/app/entities/insumo/insumo.model';

describe('Component Tests', () => {

    describe('Insumo Management Detail Component', () => {
        let comp: InsumoDetailComponent;
        let fixture: ComponentFixture<InsumoDetailComponent>;
        let service: InsumoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [InsumoDetailComponent],
                providers: [
                    InsumoService
                ]
            })
            .overrideTemplate(InsumoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InsumoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InsumoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Insumo(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.insumo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
