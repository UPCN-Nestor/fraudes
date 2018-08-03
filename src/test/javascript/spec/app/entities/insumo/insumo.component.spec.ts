/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { InsumoComponent } from '../../../../../../main/webapp/app/entities/insumo/insumo.component';
import { InsumoService } from '../../../../../../main/webapp/app/entities/insumo/insumo.service';
import { Insumo } from '../../../../../../main/webapp/app/entities/insumo/insumo.model';

describe('Component Tests', () => {

    describe('Insumo Management Component', () => {
        let comp: InsumoComponent;
        let fixture: ComponentFixture<InsumoComponent>;
        let service: InsumoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [InsumoComponent],
                providers: [
                    InsumoService
                ]
            })
            .overrideTemplate(InsumoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InsumoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InsumoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Insumo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.insumos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
