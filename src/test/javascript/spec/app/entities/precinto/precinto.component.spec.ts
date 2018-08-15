/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { PrecintoComponent } from '../../../../../../main/webapp/app/entities/precinto/precinto.component';
import { PrecintoService } from '../../../../../../main/webapp/app/entities/precinto/precinto.service';
import { Precinto } from '../../../../../../main/webapp/app/entities/precinto/precinto.model';

describe('Component Tests', () => {

    describe('Precinto Management Component', () => {
        let comp: PrecintoComponent;
        let fixture: ComponentFixture<PrecintoComponent>;
        let service: PrecintoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [PrecintoComponent],
                providers: [
                    PrecintoService
                ]
            })
            .overrideTemplate(PrecintoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrecintoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrecintoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Precinto(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.precintos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
